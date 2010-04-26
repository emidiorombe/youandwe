from django.conf.urls.defaults import *
from cdesign.app.views import v_general
from cdesign.app.views import v_user
from cdesign import general_conf

from django.contrib import admin
admin.autodiscover()

#General Mapping
urlpatterns = patterns('',
    ('^$', v_general.index),
    ('^index/$', v_general.index),
    (r'^i18n/', include('django.conf.urls.i18n')),
)

#User Mapping
urlpatterns += patterns('',
    ('^user/add/$', v_user.add_user),
    ('^user/profile/(\d+)/$', v_user.view_profile),
    ('^user/portfolio/(\d+)/$', v_user.view_portfolio),
)

#Infrastructure services
urlpatterns += patterns('',
   (r'^infra/photo/(?P<path>.*)$', 'django.views.static.serve',
        {'document_root': general_conf.TMP_PHOTO_DIR}),
   (r'^infra/css/(?P<path>.*)$', 'django.views.static.serve',
        {'document_root': general_conf.CSS_DIR}),
   (r'^infra/js/(?P<path>.*)$', 'django.views.static.serve',
        {'document_root': general_conf.JS_DIR}),

    ('^ajax/$', v_user.find_user),
)



#Admin Mapping
urlpatterns += patterns('',
    (r'^admin/', include(admin.site.urls)),
    )

handler404 = 'v_general.erro404'
