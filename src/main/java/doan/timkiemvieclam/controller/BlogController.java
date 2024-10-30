package doan.timkiemvieclam.controller;

import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.Blogs;
import doan.timkiemvieclam.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/Blog")

public class BlogController {
    @Autowired
    private BlogService BlogServices;

    @GetMapping
    public String showBlog() {
        return "admin/Blogs/List";
    }
    @GetMapping("/data")
    @ResponseBody
    public List<Blogs> getAllBlog(){
        return BlogServices.getAllBlogs();
    }

    @GetMapping("/Create")
    public String createAccount()
    {
        return "admin/Blogs/Create";
    }



    @PostMapping
    public ResponseEntity<Blogs> addBlog(@RequestBody Blogs blog) {
        Blogs newblog = BlogServices.saveBlog(blog);// Lưu blog
        return ResponseEntity.status(HttpStatus.CREATED).body(newblog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Integer id) {
        try {
            BlogServices.deleteBlogById(id);
            return ResponseEntity.noContent().build(); // Trả về 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }
    @GetMapping("/Edit/{id}")
    public String editBlogPage(@PathVariable("id") Integer blogId) {
        // Trả về trang chỉnh sửa
        return "admin/Blogs/Edit"; // Tên của trang HTML
    }
    @GetMapping("/{id}")
    public ResponseEntity<Blogs> getBlogById(@PathVariable Integer id) {
        Optional<Blogs> blogOptional = BlogServices.getBlogById(id);
        if (blogOptional.isPresent()) {
            return ResponseEntity.ok(blogOptional.get()); // Trả về 200 OK cùng với thông tin blog
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blogs> updateBlog(@PathVariable Integer id, @RequestBody Blogs blogs) {
        Optional<Blogs> existingBlog = BlogServices.getBlogById(id);
        if (existingBlog.isPresent()) {
            blogs.setBlogId(id);
            Blogs updatedBlog = BlogServices.saveBlog(blogs);
            return ResponseEntity.ok(updatedBlog);
        }
        return ResponseEntity.notFound().build();
    }



}
