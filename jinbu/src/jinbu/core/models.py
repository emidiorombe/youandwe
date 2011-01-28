from django.db import models

class Oferta(models.Model):
    texto = models.CharField(max_length=255)