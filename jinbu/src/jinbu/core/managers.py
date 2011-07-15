'''
Created on Mar 14, 2011

@author: Rafael Nunes
'''
from django.db import models

class PromocaoManager(models.Manager):
    def get_top_categorias(self):
        from jinbu.core.models import Promocao
        top_cat = Promocao.objects.all().order_by('-interessados').distinct('categoria.id')[:3]
        return top_cat
        
    def get_promocoes_das_categorias(self, top_cat):
        from jinbu.core.models import Promocao
        top_promo = {}
        for p in top_cat:
            list = Promocao.objects.filter(categoria=p.categoria)
            list_promo=[]
            for promo in list:
                list_promo.append(promo)
            top_promo[p.categoria.nome] = list_promo
        return top_promo
    
    def get_for_index(self):
        from jinbu.core.models import Promocao
        top_cat = Promocao.objects.all().order_by('-interessados').distinct('categoria.id')[:3]
        top_promo = {}
        for p in top_cat:
            list = Promocao.objects.filter(categoria=p.categoria)
            list_promo=[]
            for promo in list:
                list_promo.append(promo)
            top_promo[p.categoria.nome] = list_promo
        return top_promo
        