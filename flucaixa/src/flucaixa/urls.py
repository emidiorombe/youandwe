from django.conf.urls.defaults import *

from flucaixa.main import v_geral

urlpatterns = patterns("",
    (r"^$", v_geral.main),
)
