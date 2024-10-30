package doan.timkiemvieclam.service;

import doan.timkiemvieclam.entity.Blogs;
import doan.timkiemvieclam.repository.BlogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Blogs> getAllBlogs() {
        return blogRepository.findAll();
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
}
