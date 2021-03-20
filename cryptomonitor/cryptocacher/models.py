from django.db import models

# Create your models here.

class CryptoCurrency(models.Model):
    market = models.CharField(max_length=32)
    crypto_currency_name = models.CharField(max_length=5)
    money_currency_name = models.CharField(max_length=5)
    price = models.DecimalField(max_digits = 8, decimal_places = 2)
    transaction_time = models.DateTimeField()

