from django.conf.urls.defaults import *
from django.contrib import admin
import settings

from core.views import v_general, v_promocao



admin.autodiscover()

#Geral
urlpatterns = patterns('',
    ('^$', v_general.index),
    ('^cadastro/usuario/$', v_general.add_user),
    ('^cadastro/empresa/$', v_general.add_empresa),
    ('^cadastro/compra/$', v_promocao.criar_promocao),
    ('^oferta/$', v_promocao.list_oferta),
    ('^compra/list/(\d*)', v_promocao.list_promocao),
    ('^empresa/$', v_general.nav_empresa),
    
    ('^news/add/$', v_general.news_add),


)

#URLs administrativas
urlpatterns += patterns('',
    url(r'^admin/', include(admin.site.urls)),
)



#Para ambiente de desenv
if settings.DEBUG:
    urlpatterns += patterns('',
        url(r'^static/(?P<path>.*)$', 'django.views.static.serve', {
            'document_root': settings.MEDIA_ROOT,
        }),
   )