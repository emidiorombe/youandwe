from django.shortcuts import render_to_response
from django.http import HttpResponseRedirect

from cdesign.app.forms import NewUserForm, UploadFileForm
from cdesign.app.models import User
from cdesign import general_conf
from cdesign.app.utils import image_utilities


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
        handle_upload(request.FILES, id_user)
        return HttpResponseRedirect('/user/profile/%s' %id_user)
    return render_to_response('user_portfolio.html', {'id_user': id_user}) 

def view_profile(request, id_user):
    user = User.objects.get(pk=id_user)
    return render_to_response('user_profile.html', locals())

def handle_upload(files, id_user):
    names = save_original(files, id_user)
    image_utilities.resize_image(names)
    
def save_original(files, id_user):
    names = []
    for k in files:
        v = files[k]
        file_name = id_user + '_' + v._name 
        dest = open(general_conf.TMP_PHOTO_DIR.__add__(file_name), 'wb+')
        for chunk in v.chunks():
            dest.write(chunk)
        dest.close()
        names.append(file_name)
    return names
