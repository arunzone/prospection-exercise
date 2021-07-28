import React from 'react';
import {
  BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Label,
} from 'recharts';

const CountChart = ({ data, label }) => (
  <BarChart
    width={500}
    height={300}
    data={data}
    margin={{
      top: 5, right: 20, left: 20, bottom: 30,
    }}
  >
    <CartesianGrid strokeDasharray='3 3' />
    <XAxis dataKey='name'>
      <Label value={label} position='bottom' />
    </XAxis>
    <YAxis label={{ value: 'Count', angle: -90, position: 'insideLeft' }} />
    <Tooltip />
    <Bar dataKey='count' name='Count' fill='#8884d8' />
  </BarChart>
)

export default CountChart
