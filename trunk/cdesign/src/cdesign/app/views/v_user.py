from django.shortcuts import render_to_response
from django.http import HttpResponseRedirect

import urllib
import urllib2

from cdesign.app.forms import NewUserForm, UploadFileForm
from cdesign.app.models import User
from cdesign.general_conf import *


def add_user(request):
    if request.method == 'POST':
        form = NewUserForm(request.POST)
        if form.is_valid():
            #Aqui manipula-se os dados
            form.save()
            return HttpResponseRedirect('/user/portfolio/%s' %form.instance.pk)
    else:
        form = NewUserForm()
    return render_to_response('add_user.html', {'form': form})
    
def view_portfolio(request, id_user):
    if request.method == 'POST':
        form = UploadFileForm(request.POST, request.FILES)
        if form.is_valid():
            handle_upload(request.FILES, id_user)
            return HttpResponseRedirect('/user/profile/%s' %id_user)
    else:
        form = UploadFileForm()
    return render_to_response('user_portfolio.html', {'form': form, 'id_user': id_user}) 

def view_profile(request, id_user):
    user = User.objects.get(pk=id_user)
    return render_to_response('user_profile.html', locals())

def handle_upload(files, id_user):
    url = URL_IMG_HANDLER_APP + "?action=entry"
    values = {'file':files['file']}
    data = urllib.urlencode(values)
    req = urllib2.Request(url, data)
    urllib2.urlopen(req)
   
    
