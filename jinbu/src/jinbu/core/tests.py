"""
This file demonstrates two different styles of tests (one doctest and one
unittest). These will both pass when you run "manage.py test".

Replace these with more appropriate tests for your application.
"""

from django.test import TestCase
from django.test.client import RequestFactory
from jinbu.core.views import v_general, v_promocao

class SimpleTest(TestCase):
    
    def test_add_promo(self):
        factory = RequestFactory()
        req = factory.get("/promocao/add")
        resp = v_promocao.criar_promocao(req)
        self.failUnlessEqual(resp.status_code, 200)


