'''
Created on Nov 12, 2010

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response, redirect
from google.appengine.api.blobstore import blobstore
from domain.utils.get_uploads import get_uploads
from domain.models import PortfolioEntry

def add(request):
    if request.method == 'GET':
        form_action = blobstore.create_upload_url('/portfolio/add/')
        post = request.session['post']
        #assert False
        return render_to_response('portfolio_edit.html', locals())
    elif request.method == 'POST':
        blobs = get_uploads(request)
        for binfo in blobs:
            p_entry = PortfolioEntry()
            p_entry.description = request.POST['txtDesc'] 
            request.session['post'] = request.POST
            p_entry.image = binfo
            p_entry.tags = ['tag']
            p_entry.put()
        return redirect('/portfolio/1')

def list(request, id_p):
    return render_to_response('index.html', locals())