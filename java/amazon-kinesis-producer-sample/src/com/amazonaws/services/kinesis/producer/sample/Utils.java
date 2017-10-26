/*
 * Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazonaws.services.kinesis.producer.sample;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

import org.json.JSONObject;


public class Utils {
    private static final Random RANDOM = new Random();
    
    /**
     * @return A random unsigned 128-bit int converted to a decimal string.
     */
    public static String randomExplicitHashKey() {
        return new BigInteger(128, RANDOM).toString(10);
    }
    
    /**
     * Generates a blob containing a UTF-8 string. The string begins with the
     * sequence number in decimal notation, followed by a space, followed by
     * padding.
     * 
     * @param sequenceNumber
     *            The sequence number to place at the beginning of the record
     *            data.
     * @param totalLen
     *            Total length of the data. After the sequence number, padding
     *            is added until this length is reached.
     * @return ByteBuffer containing the blob
     */
    public static ByteBuffer generateData(long sequenceNumber, int totalLen) {
        StringBuilder sb = new StringBuilder();
        sb.append(Long.toString(sequenceNumber));
        sb.append(" ");
        while (sb.length() < totalLen) {
            sb.append("a");
        }
        try {
            return ByteBuffer.wrap(sb.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static ByteBuffer generatePcapUserEventData(Long sequenceNumber) {
    	
    		JSONObject record = new JSONObject();    		
    		record.put("createdDate", System.currentTimeMillis());
    		record.put("recordType", "USER_EVENT");
    		JSONObject correlationIds = new JSONObject();
    		final String userGuid =  "EventDataPipeline-loadtest-fakeUserGuid-"+sequenceNumber;
    		correlationIds.put("userGuid", userGuid);
    		record.put("correlatiionIds", correlationIds);
    		String eventUuid = UUID.randomUUID().toString();
    		record.put("id", eventUuid);
    		
    		JSONObject body = new JSONObject();
    		body.put("eventId", RANDOM.nextLong());
    		body.put("tokenId", RANDOM.nextInt());
    		body.put("userSiteId", RANDOM.nextInt());
    		body.put("id", eventUuid);
    		body.put("eventUuid", eventUuid);
    		body.put("eventType", "ACCOUNT_VERIFICATION_SENT");
    		body.put("sessionId", "0E5EE5BA5185054AC6945E81D59B3CE3");
    		body.put("ipAddrString", "127.0.0.1");
    		body.put("userGuid", userGuid);
    		body.put("userId", RANDOM.nextLong());
    		body.put("geo", "{\"COUNTRY\":\"US\",\"IP\":\"70.35.47.190\",\"ISP\":\"unspecified\",\"ORG\":\"unspecified\"}");
    		body.put("clientType", "WEB");
    		body.put("detail", "Product Added by UserProductManager:  | *UserProduct* | Name: Wells Fargo Bank - Investments Id: 1764 Product Name: Wells Fargo Bank - Investments Yodlee CS Id: 10862 aggregationInProgress: null User: jay@personalcapital.com");
    		body.put("status", "NOT_VIEWED");
    		
    		record.put("body", body);
    		             
        try {
            return ByteBuffer.wrap(record.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
