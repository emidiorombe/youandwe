'''
Created on Nov 12, 2010

@author: Rafael Nunes
'''
import logging as log
from django.shortcuts import render_to_response, redirect
from django.views.decorators.csrf import csrf_exempt, csrf_protect
from google.appengine.api.blobstore import blobstore
from core.utils.get_uploads import get_uploads
from core.forms import PortfolioEntryForm
from core.utils.utils import split_tags_into_models, secure_render_response
from core.views.decorators import login_required
from core.models import PortfolioEntry
from google.appengine.api import images

#### === MAPPED METHODS ===###

@csrf_exempt
@login_required
def add(request):
    if request.method == 'GET':
        form_action = blobstore.create_upload_url("/portfolio/add/")
        return render_to_response('portfolio_edit.html', locals())
    elif request.method == 'POST':
        blobs = get_uploads(request)
        for binfo in blobs:
            p_entry_form = PortfolioEntryForm(request.POST)
            if p_entry_form.is_valid():
                p_entry = p_entry_form.save(commit=False)
                p_entry.image = str(binfo.key())
                #p_entry.tags = request.POST['tags']
                p_entry.save()
                return redirect('/portfolio/'+ str(p_entry.id))
            else:
                log.error(p_entry_form.errors)
                return redirect('/portfolio/add/')

def list(request, id_p):
    pe = PortfolioEntry.objects.get(pk=id_p)
    img_url = images.get_serving_url(pe.image, size=120)
    return render_to_response('index.html', locals())

