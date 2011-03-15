'''
Created on Jan 6, 2011

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response
from django.views.decorators.cache import cache_page
from django.core.cache import cache
from jinbu.core.models import Promocao

def index(request):
    my_var = cache.get("m")
    if my_var is not None:
        cache.set("m", my_var + 1)
    Promocao.objects.get_for_index()
    return render_to_response('index.xhtml', locals())

def add_user(request):
    return render_to_response('cadastroFisico.xhtml', locals())