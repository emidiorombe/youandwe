'''
Created on Jan 28, 2011

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response

def criar_promocao(request):
    if request.method == 'GET':
        return render_to_response('cadastroPromocao.xhtml', locals())
    elif request.method == 'POST':
        return render_to_response('view_promocao.xhtml', locals())

def list_oferta(request):
    return render_to_response('oferta.xhtml')    
    
def list_promocao(request, promo_id=0):
    return render_to_response('promocao.xhtml')