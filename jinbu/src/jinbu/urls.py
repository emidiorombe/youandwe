from django.conf.urls.defaults import *
from django.contrib import admin
import settings

from core.views import v_general, v_promocao



# admin.autodiscover()

#Geral
urlpatterns = patterns('',
    ('^$', v_general.index),
    ('^cadastro/usuario/$', v_general.add_user),
    ('^cadastro/empresa/$', v_general.add_empresa),

)

#
urlpatterns += patterns('',
    ('^cadastro/compra/$', v_promocao.criar_promocao),
    ('^oferta/$', v_promocao.list_oferta),
    ('^compra/list/(\d*)', v_promocao.list_promocao),
    
)



#Para ambiente de desenv
if settings.DEBUG:
    urlpatterns += patterns('',
        url(r'^static/(?P<path>.*)$', 'django.views.static.serve', {
            'document_root': settings.MEDIA_ROOT,
        }),
   )