import React, { useState } from 'react'
import { Button, Form } from 'semantic-ui-react'

const TextInput = ({ onApply }) => {
  const [ value, setValue ] = useState('')

  return (
    <div>
      <Form>
        <Form.Field
          control={Form.TextArea}
          rows={5}
          placeholder='Type here...'
          onChange={(event, data) => setValue(data.value)}
          value={value}
        />
        <Form.Field
          control={Button}
          primary
          onClick={() => onApply(value.trim())}
        >
          Analyse
        </Form.Field>
      </Form>
    </div>
  )
}

export default TextInput