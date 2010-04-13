from django.db import models

class User(models.Model):
    name = models.CharField(max_length=30)
    mail = models.CharField(max_length=50)
    description = models.TextField(max_length=200)
    state = models.CharField(max_length=30)
    website = models.URLField()
    
    def __unicode__(self):
        return self.name