from django.conf.urls.defaults import *
from django.contrib import admin

from core.views import v_general, v_promocao



# admin.autodiscover()

#Geral
urlpatterns = patterns('',
    ('^$', v_general.index),
)

urlpatterns += patterns('',
    ('^promocao/add$', v_promocao.criar_promocao),
    ('^oferta/$', v_promocao.list_oferta),
    ('^promocao/list/(\d*)', v_promocao.list_promocao),
    
)

