document.getElementById("cvForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Ngừng hành động mặc định của form

    const cvData = {
        fullName: document.getElementById("fullName").value,
        dateOfBirth: document.getElementById("dateOfBirth").value,
        address: document.getElementById("address").value,
        phoneNumber: document.getElementById("phoneNumber").value,
        skill: document.getElementById("skill").value,
        careerObjective: document.getElementById("careerObjective").value,
        education: document.getElementById("education").value,
        project: document.getElementById("project").value
    };

    console.log(cvData);

    // Gửi dữ liệu tới server qua API
    fetch("/api/cv", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cvData)
    })
    .then(response => response.json())
    .then(data => {
        alert("Tạo CV thành công!");
        // Xử lý phản hồi từ server
    })
    .catch(error => {
        console.error('Lỗi:', error);
    });
});
