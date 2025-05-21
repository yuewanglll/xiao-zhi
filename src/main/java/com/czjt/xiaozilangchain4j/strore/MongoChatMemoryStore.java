package com.czjt.xiaozilangchain4j.strore;

import com.czjt.xiaozilangchain4j.pojo.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.List;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

        @Autowired
        private MongoTemplate mongoTemplate;
        @Override
        public List<ChatMessage> getMessages(Object memoryId) {
            Criteria criteria = Criteria.where("memoryId").is(memoryId);
            Query query = new Query(criteria);
            ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
            if(chatMessages == null) return new LinkedList<>();
            //将mongodb获取的数据序列化为chatMessage
            return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
        }


        @Override
        public void updateMessages(Object memoryId, List<ChatMessage> messages) {
            Criteria criteria = Criteria.where("memoryId").is(memoryId);
            Query query = new Query(criteria);
            Update update = new Update();
            update.set("content", ChatMessageSerializer.messagesToJson(messages));
            //根据query条件能查询出文档，则修改文档；否则新增文档
            mongoTemplate.upsert(query, update, ChatMessages.class);
        }
        @Override
        public void deleteMessages(Object memoryId) {
            Criteria criteria = Criteria.where("memoryId").is(memoryId);
            Query query = new Query(criteria);
            mongoTemplate.remove(query, ChatMessages.class);
        }
    }

