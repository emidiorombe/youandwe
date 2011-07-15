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
    promos = Promocao.objects.get_for_index()
    c_tits = {'tit1':promos.keys()[0], 'tit2':promos.keys()[1], 'tit3':promos.keys()[2]}
    return render_to_response('index.xhtml', locals())

def add_user(request):
    return render_to_response('cadastroFisico.xhtml', locals())

def add_empresa(request):
    msg = 'teste'
    return render_to_response('cadastroEmpresa.xhtml', locals())