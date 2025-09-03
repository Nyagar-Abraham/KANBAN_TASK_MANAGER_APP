package org.abraham.kanbantaskmanager.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.abraham.kanbantaskmanager.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class BoardResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User createdBy;
    private Boolean isActive;
    private String color;
    private Set<BoardColumnResponse> columns;
//    private List<BoardMemberResponse> members;
}

//type BoardResponse{
//id:ID!
//name:String!
//description: String!
//columns:[BoardColumnResponse!]!
//createdAt: String!
//updatedAt: String
//CreatedBy:Int!
//isActive: Boolean!
//color: String
//}
