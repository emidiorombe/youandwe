from django.conf.urls.defaults import *

from domain.views import v_general, v_portfolio

#Geral
urlpatterns = patterns('',
    ('^$', v_general.index),
    
)

#Portfolio
urlpatterns += patterns('',
   ('^portfolio/add/$', v_portfolio.add),
   ('^portfolio/(\d*)$', v_portfolio.list),
)

handler404 = 'v_general.erro404'
handler500 = 'v_general.erro500'