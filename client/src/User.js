import React, { useState } from "react";
import { Button, Modal, InputGroup, Form, FormControl } from "react-bootstrap";

function CenteredModal(props) {
  return (
    <Modal {...props} size="lg" centered>
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">회원 가입</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email address</Form.Label>
            <Form.Control type="email" placeholder="Enter email" />
            <Form.Text className="text-muted">We'll never share your email with anyone else.</Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control type="password" placeholder="Password" />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicCheckbox">
            <Form.Check type="checkbox" label="Check me out" />
          </Form.Group>
          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Form>

        {/* <InputGroup>
          <InputGroup.Text id="inputGroup-sizing-default">Default</InputGroup.Text>
          <FormControl aria-label="Default" aria-describedby="inputGroup-sizing-default" />

          <InputGroup.Radio aria-label="Radio button for following text input" />
          <FormControl aria-label="Text input with radio button" />
        </InputGroup> */}
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onHide}>Close</Button>
      </Modal.Footer>
    </Modal>
  );
}

function User() {
  const [modalShow, setModalShow] = useState(false);
  return (
    <div>
      <h1>User Component</h1>

      <Button variant="success" onClick={() => setModalShow(true)}>
        회원가입
      </Button>

      <CenteredModal show={modalShow} onHide={() => setModalShow(false)}></CenteredModal>
    </div>
  );
}

export default User;
