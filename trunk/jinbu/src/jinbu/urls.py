from django.conf.urls.defaults import *
from core.views import v_general, v_promocao


# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

#Geral
urlpatterns = patterns('',
    ('^$', v_general.index),
    
)

urlpatterns += patterns('',
    ('^/promocao/add$', v_promocao.criar_promocao),
    
)

