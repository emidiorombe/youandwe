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
    categorias = Promocao.objects.get_top_categorias()
    promos = Promocao.objects.get_promocoes_das_categorias(categorias)
    c_tits = {}
    if(promos.__len__() > 0):
        c_tits = {'tit1':promos.keys()[0], 'tit2':promos.keys()[0], 'tit3':promos.keys()[0]}
    return render_to_response('index.xhtml', locals())

def add_user(request):
    return render_to_response('cadastroFisico.xhtml', locals())