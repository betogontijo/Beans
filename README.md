# Beans - Conversor de moedas

## Introdução
    Descrição: aplicação do tipo cliente servidor que faz conversão de moedas.
    
## Tecnologias
    Linguagem: Java.
    
    Framework: Spring, RMI. [1]
    
    API: XChange.
## Arquitetura
    Servidor
    
        Adquirir dados da XChange API. [2]
      
        Realizar conversão de moeda (USD, BTC, ETH).
            
        Enviar os resultados para o cliente.
      
    Cliente
    
        Interação entre usuário e o servidor.
      
        Enviar requisição.

[1]https://github.com/timmolter/XChange

[2]https://spring.io/
