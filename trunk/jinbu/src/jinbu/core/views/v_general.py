'''
Created on Jan 6, 2011

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response
from jinbu.core.models import Promocao

def index(request):
    promos = Promocao.objects.get_for_index()
    list_p1 = promos.get(promos.keys()[0])
    list_p2 = promos.get(promos.keys()[1])
    list_p3 = promos.get(promos.keys()[2])
    c_tits = {'tit1':promos.keys()[0], 'tit2':promos.keys()[1], 'tit3':promos.keys()[2]}
    return render_to_response('index.xhtml', locals())

def add_user(request):
    return render_to_response('cadastroFisico.xhtml', locals())

def add_empresa(request):
    msg = 'teste'
    return render_to_response('cadastroEmpresa.xhtml', locals())