'''
Created on Jan 28, 2011

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response

def criar_usuario(request):
    if request.method == 'GET':
        return render_to_response('add_user.html', locals())
    elif request.method == 'POST':
        #cadastrar
        return render_to_response('perfil_usuario.html', locals())
    