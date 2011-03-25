# Django settings for Jinbu project.
import os

DEBUG = True
TEMPLATE_DEBUG = DEBUG

ADMINS = (
    # ('Rafael Nunes', 'rafael@jinbu.com.br'),
)

MANAGERS = ADMINS

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.mysql', # Add 'postgresql_psycopg2', 'postgresql', 'mysql', 'sqlite3' or 'oracle'.
        'NAME': 'jinbu',                      # Or path to database file if using sqlite3.
        'USER': 'root',                      # Not used with sqlite3.
        'PASSWORD': 'mysql',                  # Not used with sqlite3.
        'HOST': 'localhost',                      # Set to empty string for localhost. Not used with sqlite3.
        'PORT': '3306',                      # Set to empty string for default. Not used with sqlite3.
    }
}

TIME_ZONE = 'America/Sao_Paulo'

LANGUAGE_CODE = 'pt-br'

SITE_ID = 1

USE_I18N = True

USE_L10N = True

#MEDIA_URL = 'http://static.jinbu.com.br'
MEDIA_URL='http"//localhost:8000/static/'
CDN_URL='/static/' 
MEDIA_ROOT='/home/rafael/Aptana Studio 3 Workspace/jinbu/src/jinbu/core/static' 
 
BUSTER_VERSION=1
#STATIC_URL = '/static/'


ADMIN_MEDIA_PREFIX = '/media/'

CACHE_BACKEND = 'memcached://127.0.0.1:11211/'

SECRET_KEY = '34cfrbv&#hy3&4hds#-h0ck$mx^z4%fmdpgr7l=g4bvu4_dq+='

TEMPLATE_LOADERS = (
    'django.template.loaders.filesystem.Loader',
    'django.template.loaders.app_directories.Loader',
#     'django.template.loaders.eggs.Loader',
)

MIDDLEWARE_CLASSES = (
    'django.middleware.common.CommonMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'debug_toolbar.middleware.DebugToolbarMiddleware',
#    'django.middleware.cache.UpdateCacheMiddleware',
#    'django.middleware.cache.FetchFromCacheMiddleware',
)

ROOT_URLCONF = 'jinbu.urls'

TEMPLATE_DIRS = (
    os.path.join(os.path.dirname(__file__), 'templates'),
)

INSTALLED_APPS = (
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.sites',
    'django.contrib.messages',
    'core',
    'debug_toolbar',
    # Uncomment the next line to enable the admin:
    # 'django.contrib.admin',
    # Uncomment the next line to enable admin documentation:
    # 'django.contrib.admindocs',
)

#Debug Tool bar
INTERNAL_IPS = ('127.0.0.1',)
