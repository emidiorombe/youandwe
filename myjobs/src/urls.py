from django.conf.urls.defaults import *

from core.views import v_general, v_portfolio, v_usuario

#Geral
urlpatterns = patterns('',
    ('^$', v_general.index),
    
)

#Portfolio
urlpatterns += patterns('',
   ('^portfolio/add/$', v_portfolio.add),
   ('^portfolio/(\d*)$', v_portfolio.list),
)

#User
urlpatterns += patterns('',
   ('^user/add/$', v_usuario.create_user),
   ('^user/login/$', v_usuario.login_user),
   ('^user/logout/$', v_usuario.logout_user),
   
)


handler404 = 'v_general.erro404'
handler500 = 'djangotoolbox.errorviews.server_error'
#handler500 = 'v_general.erro500'