"""
This file demonstrates two different styles of tests (one doctest and one
unittest). These will both pass when you run "manage.py test".

Replace these with more appropriate tests for your application.
"""

from django.test import TestCase
from django.test.client import RequestFactory
from jinbu.core.views import v_general

class SimpleTest(TestCase):
    
    def setup(self):
        self.factory = RequestFactory()
        
    def test_basic_addition(self):
        """
        Tests index.
        """
        factory = RequestFactory()
        req = factory.get("/")
        resp = v_general.index(req)
        self.failUnlessEqual(resp.status_code, 200)


