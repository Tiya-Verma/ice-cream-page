import { Canvas } from '@react-three/fiber'
import { OrbitControls, Environment } from '@react-three/drei'
import { Suspense, useRef } from 'react'
import * as THREE from 'three'

function SpinningBox() {
  const meshRef = useRef<THREE.Mesh>(null)
  return (
    <mesh ref={meshRef} rotation={[0.5, 0.5, 0]} castShadow receiveShadow>
      <boxGeometry args={[1, 1, 1]} />
      <meshStandardMaterial color="#8AC" roughness={0.4} metalness={0.1} />
    </mesh>
  )
}

export default function Scene() {
  return (
    <Canvas shadows camera={{ position: [3, 3, 3], fov: 50 }} style={{ width: '100%', height: '100%' }}>
      <color attach="background" args={[0.03, 0.03, 0.05]} />
      <ambientLight intensity={0.3} />
      <directionalLight position={[5, 5, 5]} intensity={1.2} castShadow />
      <Suspense fallback={null}>
        <SpinningBox />
        <Environment preset="city" />
      </Suspense>
      <OrbitControls makeDefault enableDamping />
    </Canvas>
  )
} 