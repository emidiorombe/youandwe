'''
Created on Mar 14, 2011

@author: Rafael Nunes
'''
from django.db import models, connection

class PromocaoManager(models.Manager):
    def get_for_index(self):
        from jinbu.core.models import Promocao
        top_cat = Promocao.objects.all().order_by('-interessados').distinct('categoria.id')[:3]
        top_promo = {}
        for p in top_cat:
            print p.categoria
            list = Promocao.objects.filter(categoria=p.categoria)
            list_promo=[]
            for promo in list:
                list_promo.append(promo)
            top_promo[p.categoria.nome] = list_promo
        return top_promo