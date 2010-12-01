from django.db import models
from djangotoolbox.fields import ListField
from django.contrib.auth.models import User


class Portfolio(models.Model):
    description = models.TextField()
    
class PortfolioEntry(models.Model):
    image_description = models.CharField(max_length=255, null=True, blank=True)
    image = models.CharField(max_length=255) #In appengine will store BlobKey
    title = models.CharField(max_length=255, null=True, blank=True)
    tags = ListField(models.CharField(max_length=255))
    creation_date = models.DateTimeField(auto_now_add=True)
    portfolio = models.ForeignKey(Portfolio, null=True, blank=True)

    
class Usuario(models.Model):
    djuser = models.ForeignKey(User, null=True)
    is_pagante = models.BooleanField() #pagante ou free
    perfil = models.PositiveIntegerField() #freelance/company/outro
    url = models.URLField()
    image_description = models.TextField()
    creation_date = models.DateTimeField(auto_now_add=True)
    portfolio = models.ForeignKey(Portfolio)

