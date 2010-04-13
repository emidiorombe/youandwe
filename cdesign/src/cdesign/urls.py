from django.conf.urls.defaults import *
from cdesign.app.views import v_general;
from cdesign.app.views import v_user

from django.contrib import admin
admin.autodiscover()

#General Mapping
urlpatterns = patterns('',
    ('^$', v_general.index),
)

#User Mapping
urlpatterns += patterns('',
    ('^user/add/$', v_user.add_user),
    ('^user/profile/(\d+)/$', v_user.view_profile),
    ('^user/portfolio/(\d+)/$', v_user.view_portfolio),
)


#Admin Mapping
urlpatterns += patterns('',
    (r'^admin/', include(admin.site.urls)),
    )

handler404 = 'v_general.erro404'
