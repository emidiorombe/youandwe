'''
Created on Jul 15, 2011

@author: rafael
'''
from django.contrib import admin
from jinbu.core.models import *

admin.site.register(Empresa)
admin.site.register(Categoria)
admin.site.register(Promocao)
admin.site.register(Oferta)
admin.site.register(Cupom)
admin.site.register(Usuario)