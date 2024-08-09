# Kafka Fraud Detection

## Description
This project is a Kafka-based fraud detection system built using Spring Boot. It processes transaction data to detect potential fraudulent activities.

## Technologies
- Java 17
- Spring Boot
- Kafka
- Docker
- Debezium
- PostgreSQL

## Usage
- **Application:** This program provides the Transaction API, Threshold API, and Alert Consumer.
- **ThresholdStream:** This program processes transaction data to detect potential fraudulent activities.

## Flow
1. **Threshold API:** The Threshold API receives threshold data and sends it to the Kafka topic.
2. **Transaction API:** The Transaction API receives transaction data and sends it to the Kafka topic.
3. **ThresholdStream:** The ThresholdStream processes transaction data and threshold data to detect potential fraudulent activities.
4. **Alert Consumer:** The Alert Consumer consumes alerts from the Kafka topic.