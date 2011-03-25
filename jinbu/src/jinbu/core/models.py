from django.db import models
from django.contrib.auth.models import User
from jinbu.core import managers

class Usuario(User):
    t = models.IntegerField(default=0)
    
class Empresa(User):
    x = models.IntegerField(default=0)
    
class Categoria(models.Model):
    nome = models.CharField(max_length=255)
    def __unicode__(self):
        return self.nome
    
class Promocao(models.Model):
    texto = models.CharField(max_length=255)
    data_cadastro = models.DateField()
    local = models.CharField(max_length=255)
    user_criacao = models.ForeignKey(Usuario)
    image = models.CharField(max_length=255)
    categoria = models.ForeignKey(Categoria)
    interessados = models.IntegerField()
    objects = managers.PromocaoManager()
    status = models.IntegerField(default=0)
    
    def __unicode__(self):
        return self.texto
    
class Oferta(models.Model):
    promocao = models.ForeignKey(Promocao)
    data_expiracao = models.DateField()
    adesao_minima = models.IntegerField()
    empresa = models.ForeignKey(Empresa)
    usuarios = models.ManyToManyField(Usuario)
    
    
class Cupom(models.Model):
    promocao = models.ForeignKey(Promocao)
    oferta = models.ForeignKey(Oferta)
    usuario = models.ForeignKey(Usuario) 