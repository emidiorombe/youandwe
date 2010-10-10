from django.conf.urls.defaults import *

from main_app.views import v_general
from main_app.views import v_infra

urlpatterns = patterns('',
    ('^$', v_general.index),
    ('^list/$', v_general.list),
    ('^add_comment/$', v_general.adicionar_comentario),
    
    ('^cron/get_tweet$', v_infra.get_tweets),
)



handler404 = 'main_app.views.v_general.erro404'
handler500 = 'main_app.views.v_general.erro500'
