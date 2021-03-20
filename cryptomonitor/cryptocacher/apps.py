from django.apps import AppConfig
from django.db.models.signals import post_migrate, connection_created

from cryptocacher.WebSocketInitialization import WebSocketInitialization

class CryptocacherConfig(AppConfig):
    name = 'cryptocacher'