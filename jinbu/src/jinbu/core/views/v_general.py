'''
Created on Jan 6, 2011

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response, render
from jinbu.core.models import Promocao
from django.views.decorators.csrf import csrf_protect
from jinbu.core.forms import EmpresaForm

def index(request):
    promos = Promocao.objects.get_for_index()
    list_p1 = promos.get(promos.keys()[0])
    list_p2 = promos.get(promos.keys()[1])
    list_p3 = promos.get(promos.keys()[2])
    c_tits = {'tit1':promos.keys()[0], 'tit2':promos.keys()[1], 'tit3':promos.keys()[2]}
    return render_to_response('index.xhtml', locals())

def add_user(request):
    return render_to_response('cadastroFisico.xhtml', locals())

@csrf_protect
def add_empresa(request):
    if request.method == 'GET':
        return render(request, 'cadastroEmpresa.xhtml', locals())
    elif request.method == 'POST':
        form = EmpresaForm(request.POST)
        empresa = form.save(commit=False)
        return render_to_response('navEmpresa.xhtml', locals())

def nav_empresa(request):
    return render_to_response('navEmpresa.xhtml', locals())
