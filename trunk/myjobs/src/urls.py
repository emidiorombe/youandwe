from django.conf.urls.defaults import *

from domain.views import v_general

urlpatterns = patterns('',
    ('^$', v_general.index),
)

handler404 = 'v_general.erro404'
handler500 = 'v_general.erro500'