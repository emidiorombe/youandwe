from django.shortcuts import render_to_response
from django.utils.translation import ugettext as msg

def index(request):
    myvar = msg('ola_enfermeira')
    return render_to_response('index.html', locals())

def erro404(request):
    return render_to_response('404.html')