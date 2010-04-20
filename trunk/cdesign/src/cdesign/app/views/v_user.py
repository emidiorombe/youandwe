from django.shortcuts import render_to_response
from django.http import HttpResponseRedirect, HttpResponse
from django.utils import simplejson


from cdesign.app.forms import NewUserForm
from cdesign.app.models import User, PortfolioEntry
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
        names = handle_upload(request.FILES, id_user)
        save_images(names, id_user)
        return HttpResponseRedirect('/user/profile/%s' %id_user)
    return render_to_response('user_portfolio.html', {'id_user': id_user}) 

def view_profile(request, id_user):
    user = User.objects.get(pk=id_user)
    user_images = PortfolioEntry.objects.all()
    return render_to_response('user_profile.html', locals())

def find_user(request):
    # Default return list
    results = []
    if request.method == "GET":
        if request.GET.has_key(u'query'):
            value = request.GET[u'query']
            # Ignore queries shorter than length 3
            if len(value) > 2:
                model_results = User.objects.filter(name__icontains=value)
                results = [ x.name for x in model_results ]
    json = simplejson.dumps(results)
    return HttpResponse(json, mimetype='application/json')

def handle_upload(files, id_user):
    names = save_original(files, id_user)
    image_utilities.resize_image(names)
    return names
    
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

def save_images(names, id_user):
    user = User.objects.get(pk=id_user)
    for name in names:
        pEntry = PortfolioEntry()
        pEntry.user = user
        pEntry.img = name
        pEntry.save() 
