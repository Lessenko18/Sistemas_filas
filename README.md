# Simulação de Fila Bancária

Simulação concorrente de um sistema de atendimento bancário em Java, utilizando threads para modelar clientes chegando e atendentes processando a fila.

## Objetivo

Determinar o **número mínimo de atendentes** necessário para que o tempo médio de espera dos clientes não ultrapasse **120 segundos** (2 minutos) em um período de atendimento de 2 horas (11h às 13h).

## Como funciona

A simulação executa de 1 até 10 atendentes, incrementando um por vez, e para na primeira configuração que atinge a meta de espera.

### Parâmetros da simulação

| Parâmetro | Valor | Descrição |
|---|---|---|
| `TEMPO_SIMULACAO` | 7200 s | Duração simulada (2 horas) |
| `CHEGADA_MIN` | 5 s | Intervalo mínimo entre chegadas |
| `CHEGADA_MAX` | 50 s | Intervalo máximo entre chegadas |
| `META_ESPERA` | 120 s | Tempo médio de espera aceitável |
| `ESCALA` | 5 | Fator de compressão do tempo real |
| `MAX_ATENDENTES` | 10 | Máximo de atendentes testados |

> O fator `ESCALA = 5` significa que 1 segundo real representa 5 segundos simulados, acelerando a execução.

### Tempo de atendimento

Cada atendente leva entre **30 e 120 segundos** (simulados) para atender um cliente, sorteado aleatoriamente com distribuição uniforme.

## Métricas reportadas por rodada

- Clientes que entraram na fila
- Clientes atendidos (throughput)
- Maior tempo de espera
- Maior tempo de atendimento
- Tempo médio de atendimento
- Lead time médio (espera + atendimento)
- Tempo médio de espera (critério da meta)

## Estrutura do projeto

```
banco/
├── Banco.java       # Ponto de entrada; orquestra as simulações
├── Atendente.java   # Thread que consome clientes da fila e registra estatísticas
├── Cliente.java     # Entidade com id, estado e tempos de espera/atendimento
└── Fila.java        # Fila thread-safe com capacidade limitada e métricas
```

## Como executar

```bash
# Compilar
javac banco/*.java

# Executar
java banco.Banco
```

## Exemplo de saída

```
============================================
 Simulacao com 3 atendente(s)
============================================
 Clientes que entraram na fila:  142
 Clientes atendidos (throughput): 142
 Maior tempo de espera:          187.4 s
 Maior tempo de atendimento:     119 s
 Tempo medio de atendimento:     74.3 s
 Lead time medio (espera+atend): 189.1 s
 Tempo medio de espera: 114.8 segundos
 >>> META ATINGIDA com 3 atendente(s)!
```
