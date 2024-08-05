package id.co.bca.kafka.beginner;

import id.co.mini.project.Transaction;
import id.co.mini.project.transaction_type;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class TransactionAvro {
//    Notes: Class TransactionAvro hanya untuk testing & schema transaction
    public static void main(String[] args) {
//        Create specific record
        Transaction.Builder transactionBuilder = Transaction.newBuilder();
        transactionBuilder.setTransactionId("1");
        transactionBuilder.setAccountNumber("1");
        transactionBuilder.setAmount(100000);
        transactionBuilder.setTransactionType(transaction_type.deposit);
        transactionBuilder.setTransactionTime(Instant.now().toEpochMilli());
        transactionBuilder.setSenderAccountNumber("0");
        transactionBuilder.setReceiverAccountNumber("1");

        Transaction transaction = transactionBuilder.build();
        System.out.println(transaction);

//        Write to file
        final DatumWriter<Transaction> datumWriter = new SpecificDatumWriter<>(Transaction.class);
        try (DataFileWriter<Transaction> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(transaction.getSchema(), new File("transaction-data.avro"));
            dataFileWriter.append(transaction);
            dataFileWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
