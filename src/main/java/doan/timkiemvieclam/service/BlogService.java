package doan.timkiemvieclam.service;

import doan.timkiemvieclam.entity.Blogs;
import doan.timkiemvieclam.repository.BlogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Blogs> getAllBlogs() {

        List<Blogs> blog = blogRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        blog.forEach(blogs -> {
            String blogDetails = blogs.getDetail();
            if (blogDetails != null) {
                // Lấy text từ HTML và rút gọn chuỗi
                String plainText = Jsoup.parse(blogDetails).text(); // Loại bỏ thẻ HTML
                if (plainText.length() > 20) {
                    plainText = plainText.substring(0, 20) + "...";
                }
                // Đưa nội dung rút gọn quay lại dạng HTML đơn giản
                String truncatedHtml = Jsoup.clean(plainText, Safelist.basic());
                blogs.setDetail(truncatedHtml);
            }

        });

        return blog;
    }

    public Optional<Blogs> getBlogById(Integer id) {
        return blogRepository.findById(id);
    }

    public Blogs saveBlog(Blogs blog) {
        return blogRepository.save(blog);
    }

    public Blogs updateBlog(Integer id, Blogs blogDetails) {
        Blogs blog = blogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found with id " + id));

        blog.setTitle(blogDetails.getTitle());
        blog.setDetail(blogDetails.getDetail());
        blog.setImage(blogDetails.getImage());
        blog.setDescription(blogDetails.getDescription());
        blog.setAccount(blogDetails.getAccount());
        blog.setIsActive(blogDetails.getIsActive());

        return blogRepository.save(blog);
    }

    public void deleteBlogById(Integer id) {
        blogRepository.deleteById(id);
    }
    public int countByBlogId() {
        return blogRepository.countByBlogId();  // Đếm các công việc đang hoạt động
    }


}
