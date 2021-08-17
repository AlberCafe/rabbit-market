import React, { useState, useEffect } from "react";

import { Button, Card, Container, Image } from "react-bootstrap";

import axios from "axios";
import { Redirect } from "react-router";

function Product(props) {
  const URL = "http://ec2-3-35-167-70.ap-northeast-2.compute.amazonaws.com:8080/api/file/upload";
  const [categoryName, setCategoryName] = useState("임시카테고리2");
  const [content, setContent] = useState("미개봉 쿨거래시 네고 가능.");
  const [price, setPrice] = useState("1000000");
  const [title, setTitle] = useState("노트북 팝니다.");

  const [Images, setImages] = useState([]);
  const [imgBase64, setImgBase64] = useState(""); // 파일 base64
  const [img, setImage] = useState(null);

  const onDrop = (files) => {
    let formData = new FormData();
    const config = {
      header: { "content-type": "multipart/form-data" },
    };
    formData.append("multipartFile", files[0]);

    axios.post(URL, formData, config).then((response) => {
      console.log(response);
      // if (response.data.success) {
      //   setImages([...Images, response.data.image]);
      // } else {
      //   alert("Failed to save the Image in Server");
      // }
    });
  };

  const onDelete = (image) => {
    const currentIndex = Images.indexOf(image);

    let newImages = [...Images];
    newImages.splice(currentIndex, 1);

    setImages(newImages);
  };

  const handleRegister = () => {
    const token = localStorage.getItem("token");

    const body = {
      categoryName: categoryName,
      content: content,
      price: price,
      title: title,
    };
    axios
      .post(URL, body, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => console.log("물건 등록 성공", res))
      .catch((err) => console.log("물건 등록 에러", err));
  };

  const onChange = (e) => {
    setImages(e.target.files[0]);

    const reader = new FileReader();
    reader.readAsDataURL(e.target.files[0]);
    reader.onloadend = (e) => {
      const {
        currentTarget: { result },
      } = e;
      setImage(result);
    };
  };

  const onClick = async () => {
    const token = localStorage.getItem("token");

    const formData = new FormData();
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "content-type": "multipart/form-data",
      },
    };
    formData.append("multipartFile", Images);
    //formData.append("multipartFile", files[0]);
    for (var value of formData.values()) {
      console.log(value);
    }

    axios.post(URL, formData, config).then((response) => {
      console.log(response);
    });
  };

  return (
    <div>
      <Container>
        <Card style={{ width: "18rem" }}>
          <Card.Img variant="top" src="holder.js/100px180" />
          <Card.Body>
            <Card.Title>Card Title</Card.Title>
            <Card.Text>Some quick example text to build on the card title and make up the bulk of the card's content.</Card.Text>
            <Button variant="primary">Go somewhere</Button>
          </Card.Body>
        </Card>
        <Button variant="primary" onClick={handleRegister}>
          Submit
        </Button>
        <div>
          <input type="file" name="file" onChange={onChange} />
          <button onClick={onClick}>제출</button>
        </div>

        <Image src={img} style={{ width: 300 }} />
      </Container>
    </div>
  );
}
export default Product;
