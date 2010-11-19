'''
Created on Nov 12, 2010

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response, redirect
from google.appengine.api.blobstore import blobstore
from domain.utils.get_uploads import get_uploads
from domain.forms import PortfolioEntryForm
from domain.utils.utils import split_tags_into_models
from domain.models import Tag

#### === MAPPED METHODS ===###
def add(request):
    if request.method == 'GET':
        form_action = blobstore.create_upload_url('/portfolio/add/')
        return render_to_response('portfolio_edit.html', locals())
    elif request.method == 'POST':
        blobs = get_uploads(request)
        for binfo in blobs:
            p_entry_form = PortfolioEntryForm(request.POST)
            if p_entry_form.is_valid():
                p_entry = p_entry_form.save(commit=False)
                p_entry.image = binfo
                p_entry.tags = get_tags(request.POST['tags'])
                p_entry.put()
            else:
                request.session['err'] = p_entry.errors
        return redirect('/portfolio/1')

def list(request, id_p):
    return render_to_response('index.html', locals())


###=== HELPER METHODS ===###
def get_tags(tags):
    tag_list = []
    for token in tags.split(','):
        tag = Tag.all().filter('name =', token.lower()).fetch(1)
        if tag.__len__() == 0:
            tag_new = Tag()
            tag_new.name = token.lower()
            tag_new.put()
            tag_list.append(tag_new.key())
        else:
            tag_list.append(tag[0].key())
    return tag_list