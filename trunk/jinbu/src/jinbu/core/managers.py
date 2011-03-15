'''
Created on Mar 14, 2011

@author: Rafael Nunes
'''
from django.db import models

class PromocaoManager(models.Manager):
    def get_for_index(self):
        from jinbu.core.models import Categoria
        #TODO Buscar as categorias mais buscadas
        cats = Categoria.objects.all().filter(nome__in=['LAZER', 'BARES', 'BELEZA'])
        print cats