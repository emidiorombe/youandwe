'''
Created on Mar 14, 2011

@author: Rafael Nunes
'''
from django.db import models

class PromocaoManager(models.Manager):
    def get_for_index(self):
        from jinbu.core.models import Promocao
        top_promos = Promocao.objects.all().order_by('-interessados').distinct('categoria')[:3]
        for p in top_promos:
            print p.categoria.id    