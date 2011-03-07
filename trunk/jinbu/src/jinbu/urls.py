from django.conf.urls.defaults import *
from django.contrib import admin

from core.views import v_general



# admin.autodiscover()

#Geral
urlpatterns = patterns('',
    ('^$', v_general.index),
    
)

urlpatterns += patterns('',
    ('^/promocao/add$', v_promocao.criar_promocao),
    
)

