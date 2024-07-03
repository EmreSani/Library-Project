package com.dev02.libraryproject.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookRequest {

    @NotNull(message = "Book name must not be null")
    private String name;

    @NotNull(message = "ISBN must not be null")
    @Size(min = 17, max = 17, message = "Field must be exactly 17 characters")
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d", message = "Field must match the format 999-99-99999-99-9")
    private String isbn;

    @NotNull(message = "Field cannot be null")
    private int pageCount;

    @NotNull(message = "Field cannot be null")
    private Long authorId;

    @NotNull(message = "Field cannot be null")

    private Long publisherId;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
//    @Past(message = "Year must be in the past.")
//    @Pattern(regexp = "^\\d{4}$", message = "Year must be a valid four-digit year")
//    private LocalDate publishDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Publish date must be in the past")
    private LocalDate publishDate;

    @NotNull(message = "Field cannot be null")
    private Long categoryId;

    @Nullable
    private File image;


    @NotNull(message = "Field cannot be null")
    private boolean loanable = true;

    @NotNull(message = "Field cannot be null")
    @Size(min = 6, max = 6, message = "Shelf code must be exactly 6 characters long")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$", message = "Shelf code must follow the format AA-999")
    private String shelfCode;

    @NotNull(message = "Field cannot be null")
    private boolean active = true;


    @NotNull(message = "Field cannot be null")
    private boolean featured;

//    @NotNull(message = "Create date cannot be null")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'-HH:mm:ssZ")
//    private LocalDateTime createDate;


    @NotNull(message = "Field cannot be null")
    private boolean builtIn;






}
